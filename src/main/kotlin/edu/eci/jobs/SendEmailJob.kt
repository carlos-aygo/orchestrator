package edu.eci.jobs

import edu.eci.services.SendEmailService
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton

@Singleton
class SendEmailJob (
    private val sendEmailService: SendEmailService
        ){

    @Scheduled(fixedRate = "1m")
    fun sendEmails(){
        this.sendEmailService.sendPendingEmails()
    }
}