# jm-pay
java spring boot
集成微信和支付宝支付sdk<br>
已完成微信native h5<br>
已完成支付宝pc app h5<br>
已完成微信jsapi<br>


wx jsapi查询订单状态
JmOrderQueryVO jmOrderQueryVO = jmWxPayJsapi.query(new JmOrderQueryParam().setOrderNo("订单号").setPayType(JmWxPayTypeConstant.WX_JSAPI));

wx jsapi 下单
JmPayParam jmPayParam = new JmPayParam()
                .setOrderNo("订单号")
                .setOrderName("订单名称")
                .setDesc("购买礼物")
                .setAmount("金额")
                .setPayerClientIp(ip)
                .setOpenid("微信openid");
 JmPayVO jmPayVO = this.jmWxPayJsapi.pay(jmPayParam);

 
