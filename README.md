
### Java SM4 or js SM4

- src 下存放Java版本SM4算法。入参出参 都是Base64转换的字符串
- 国密.html 下存放 Js 版本SM4算法。入参出参 都是Base64转换的字符串

### SM4 包含 CBC 算法。以及 ECB算法

- * CBC算法需要 两个参数 iv 以及 secretKey 
- * ECB算法需要 一个参数 secretKey 
- * 密钥 以及 Iv 建议用 16 位的随机字符串