package edu.eci.services

import edu.eci.models.SendEmail

interface SendEmailService {

    fun create(email: SendEmail): SendEmail
    fun sendPendingEmails()
    fun cancelSendEmail(id: Long)
}