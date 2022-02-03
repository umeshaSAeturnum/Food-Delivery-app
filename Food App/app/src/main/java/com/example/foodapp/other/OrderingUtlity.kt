package com.example.foodapp.other

import java.lang.Exception
import java.lang.StringBuilder
import java.security.MessageDigest

object OrderingUtlity {
    private fun toMD5Hash(text: String): String {
        var result = ""

        try {
            val md5 = MessageDigest.getInstance("MD5")
            val md5HashBytes = md5.digest(text.toByteArray()).toTypedArray()

            result = byteArrayToHexString(md5HashBytes)
        } catch (e: Exception) {
            result = "error : ${e.message}"
        }

        return result
    }


    private fun byteArrayToHexString(array: Array<Byte>): String {
        var result = StringBuilder(array.size * 2)

        for (byte in array) {
            val toAppend = String.format("%2X", byte).replace(" ", "0")
            result.append(toAppend).append("_")
        }

        result.setLength(result.length - 1)
        return result.toString()
    }

    fun passwordTomd5Hash(password: String) : String{
        return toMD5Hash(password)
    }



}