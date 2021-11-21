package edu.eci.services.flows

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import edu.eci.entities.MetadataFlow
import edu.eci.entities.SendEmailMessage
import edu.eci.enums.FlowEnum
import edu.eci.models.SendEmail
import edu.eci.services.SendEmailService
import edu.eci.utils.JsonExtension.processString
import jakarta.inject.Singleton
import java.time.LocalDateTime

@Singleton
class SendEmailMessageFlow(
    private val sendEmailService: SendEmailService
): ExecutionFlow {
    override fun getFlow(): FlowEnum {

        return FlowEnum.SEND_EMAIL_MESSAGE
    }

    override fun execute(data: JsonNode, flow: MetadataFlow) {

        flow as SendEmailMessage

        this.sendEmailService.create(
            SendEmail().apply {
                this.fromMail = flow.from
                this.toMail = flow.to
                this.subject = flow.subject
                this.content = flow.message.processString(data as ObjectNode)
                this.scheduledAt = LocalDateTime.now().plusMinutes(flow.delay)
            }
        )
    }
}