import CryptoJS from 'crypto-js'
import JsEncrypt from 'jsencrypt'

export default {
    random: function(length){
        let s = '1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
        var r = ''
        let i = 0
        while(i<length){
            let index = Math.floor(Math.random()*s.length)
            r = r + s[index]
            i ++
        }
        return r
    },
    encrypt: function(content, key){
        return CryptoJS.enc.Base64.stringify(CryptoJS.DES.encrypt(content,CryptoJS.enc.Utf8.parse(key),{
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7,
            iv: CryptoJS.enc.Utf8.parse(key)
        }).ciphertext)
    },
    rsaEncrypt: function(content, publicKey){
        let jse = new JsEncrypt()
        jse.setPublicKey(publicKey)
        return jse.encrypt(content)
    }
}