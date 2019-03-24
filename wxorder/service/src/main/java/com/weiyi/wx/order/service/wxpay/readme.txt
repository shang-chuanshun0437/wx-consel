        /*微信支付：调用统一接口，必传字段
        appid   微信支付分配的公众账号ID（企业号corpid即为此appId）
		mch_id   微信支付分配的商户号
		nonce_str  随机字符串，长度要求在32位以内。推荐随机数生成算法
		sign       通过签名算法计算得出的签名值，详见签名生成算法


		body       商品简单描述，该字段请按照规范传递，具体请见参数规定
		out_trade_no  商户系统内部订单号，要求32个字符内，只能是数字、大小写字母
		total_fee      订单总金额，单位为分，详见支付金额
		spbill_create_ip 支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
		notify_url   异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。

		trade_type    JSAPI -JSAPI支付 ；NATIVE -Native支付 ；APP -APP支付

		openid
		trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
		openid如何获取，可参考【获取openid】。
		企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
		*/