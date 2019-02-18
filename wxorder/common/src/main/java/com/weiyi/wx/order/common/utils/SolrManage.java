package com.weiyi.wx.order.common.utils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SolrManage
{
    private static String BASE_URL = "http://192.168.1.4:8080/solr/newCore";

    //添加文档，如果ID存在则替换，不存在则新建
    public static void addDocument(SolrEntity solrEntity) throws Exception
    {
        HttpSolrClient httpSolrClient = getHttpSolrClient();

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",solrEntity.getId());
        document.addField("product_name",solrEntity.getProductName());
        document.addField("product_price",solrEntity.getProductPrice());
        document.addField("product_description",solrEntity.getProductDescription());
        document.addField("product_picture",solrEntity.getProductPicture());
        document.addField("product_catalog_name",solrEntity.getProductCatalogName());
        UpdateResponse response = httpSolrClient.add(document);
        httpSolrClient.commit();
        httpSolrClient.close();
        if (response.getStatus() == 0){
            System.out.println("添加成功");
        }
    }

    //根据ID删除文档
    public static void deleteDocumentById(String id) throws Exception
    {
        HttpSolrClient httpSolrClient = getHttpSolrClient();

        UpdateResponse response = httpSolrClient.deleteById(id);
        httpSolrClient.commit();
        httpSolrClient.close();
        if (response.getStatus() == 0){
            System.out.println("删除成功");
        }
    }

    //删除所有文档
    public static void deleteDocumentAll() throws Exception
    {
        HttpSolrClient httpSolrClient = getHttpSolrClient();

        UpdateResponse response = httpSolrClient.deleteByQuery("*:*");
        httpSolrClient.commit();
        httpSolrClient.close();
        if (response.getStatus() == 0){
            System.out.println("删除成功");
        }
    }

    //根据查询条件查询文档（要根据具体的业务需求进行设计）
    public static void queryDocument() throws Exception
    {
        HttpSolrClient httpSolrClient = getHttpSolrClient();

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q","product_name:小米");

        //设置过滤条件
        solrQuery.set("fq","product_price:[0 TO 100]");

        //设置排序:asc 升序  desc降序
        solrQuery.addSort("product_price", SolrQuery.ORDER.desc);

        //设置分页
        solrQuery.setStart(0);//开始位置
        solrQuery.setRows(3);//每页3条

        //开启高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");//设置高亮域
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");//设置高亮样式,设置为红色，可以根据需求修改
        solrQuery.setHighlightSimplePost("</span>");

        //查询
        QueryResponse response = httpSolrClient.query(solrQuery);

        //获取查询结果
        SolrDocumentList solrDocumentList = response.getResults();

        //获取高亮的内容
        Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();

        //查询的记录数
        long total = solrDocumentList.getNumFound();
        System.out.println("总数量：" + total);

        for (SolrDocument document : solrDocumentList){
            String id = (String)document.getFieldValue("id");
            String productName = (String)document.getFieldValue("product_name");
            Float productPrice = (Float) document.getFieldValue("product_price");
            String productDescription = (String)document.getFieldValue("product_description");

            if (highlightingMap != null && highlightingMap.size() > 0){
                //根据ID获取高亮域的内容
                Map<String, List<String>> contentMap = highlightingMap.get(id);
                //根据具体的域获取高亮内容
                List<String> list = contentMap.get("product_name");
                if (list != null && list.size() > 0){
                    System.out.println("高亮内容为：" + list.get(0));
                }
            }

            System.out.println("id=" + id +"productName=" + productName +
                    "productPrice=" + productPrice +
                    "productDescription=" + productDescription);
        }

        httpSolrClient.close();
    }
    //创建solr的客户端连接对象
    private static HttpSolrClient getHttpSolrClient(){
        return  new HttpSolrClient.Builder(BASE_URL)
                .withConnectionTimeout(10000).withSocketTimeout(6000).build();
    }
}
