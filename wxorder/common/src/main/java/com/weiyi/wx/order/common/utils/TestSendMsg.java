package com.weiyi.wx.order.common.utils;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        //测试添加
        SolrEntity solrEntity = new SolrEntity();
        solrEntity.setId("11");
        solrEntity.setProductName("小米Note版，无限精彩");
        solrEntity.setProductPrice(50.03f);
        solrEntity.setProductDescription("欢迎购买");
        solrEntity.setProductCatalogName("手机");
        solrEntity.setProductPicture("http://www.baidu.com");
        //SolrManage.addDocument(solrEntity);

        //测试查询
        //SolrManage.queryDocument();

        //测试删除
        //SolrManage.deleteDocumentById("11");

    }
}
