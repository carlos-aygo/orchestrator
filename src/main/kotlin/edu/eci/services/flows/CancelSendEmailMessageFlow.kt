package edu.eci.services.flows

import com.amazonaws.regions.Regions
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import edu.eci.entities.CancelSendEmailMessage
import edu.eci.entities.MetadataFlow
import edu.eci.entities.SendEmailMessage
import edu.eci.enums.FlowEnum
import edu.eci.models.SendEmail
import edu.eci.services.SendEmailService
import edu.eci.utils.JsonExtension.processString
import jakarta.inject.Singleton
import java.time.LocalDateTime

@Singleton
class CancelSendEmailMessageFlow(
    private val sendEmailService: SendEmailService
): ExecutionFlow {
    override fun getFlow(): FlowEnum {

        return FlowEnum.CANCEL_SEND_EMAIL_MESSAGE
    }

    override fun execute(data: JsonNode, flow: MetadataFlow) {

        flow as CancelSendEmailMessage

        this.sendEmailService.cancelSendEmail(
            flow.path.processString(data as ObjectNode).toLong()
        )
    }
}