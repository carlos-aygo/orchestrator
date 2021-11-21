package edu.eci.services

import com.amazonaws.regions.Regions
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.*
import edu.eci.models.SendEmail
import edu.eci.repositories.SendEmailRepository
import jakarta.inject.Singleton
import java.time.LocalDateTime

@Singleton
class SendEmailServiceImpl(
    private val sendEmailRepository: SendEmailRepository
): SendEmailService {

    override fun create(email: SendEmail): SendEmail {

        return this.sendEmailRepository.save(email)
    }

    override fun sendPendingEmails() {

        val messages = this.sendEmailRepository.findAll()
            .filter { sendEmail ->
                sendEmail.scheduledAt <= LocalDateTime.now()
            }
        messages.forEach { this.sendEmail(it) }
        this.sendEmailRepository.deleteAll(messages)
    }

    private fun sendEmail(sendEmail: SendEmail){

        val client = AmazonSimpleEmailServiceClientBuilder
            .standard()
            .withRegion(Regions.US_EAST_1)
            .build()

        val request = SendEmailRequest()
            .withDestination(
                Destination().withToAddresses(sendEmail.toMail)
            )
            .withMessage(
                Message()
                    .withBody(
                        Body()
                            .withText(
                                Content()
                                    .withCharset("UTF-8")
                                    .withData(sendEmail.content)
                            )
                    ).withSubject(
                        Content()
                            .withCharset("UTF-8")
                            .withData(sendEmail.subject)
                    )
            )
            .withSource(sendEmail.fromMail)

        client.sendEmail(request)
    }

    override fun cancelSendEmail(id: Long) {

        val toDelete = this.sendEmailRepository.findAll().filter { element ->
            element.referenceId == id
        }

        this.sendEmailRepository.deleteAll(toDelete)
    }
}