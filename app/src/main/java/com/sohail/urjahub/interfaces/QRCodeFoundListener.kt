package com.sohail.urjahub.interfaces

interface QRCodeFoundListener {
    fun onQrCodeFound(QRCode: String)
    fun QrCodeNotFound()
}