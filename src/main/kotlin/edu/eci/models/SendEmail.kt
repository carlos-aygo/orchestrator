package edu.eci.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import edu.eci.utils.DateUtil
import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
@Entity
@Table(name = "send_emails")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
open class SendEmail {

    @Id
    @field:JsonProperty("id")
    @SequenceGenerator(name = "send_emails_id_seq", sequenceName = "send_emails_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "send_emails_id_seq")
    open var id: Long = 0

    @field:JsonProperty("reference_id")
    @Column(name = "reference_id", nullable = false)
    @NotNull
    @NotBlank
    open var referenceId: Long = 0

    @field:JsonProperty("from_mail")
    @Column(name = "from_mail", nullable = false)
    @NotNull
    @NotBlank
    open var fromMail: String = ""

    @NotNull
    @NotBlank
    @field:JsonProperty("to_mail")
    @Column(name = "to_mail", nullable = false)
    open var toMail: String = ""

    @NotNull
    @NotBlank
    @field:JsonProperty("subject")
    @Column(name = "subject", nullable = false)
    open var subject: String = ""

    @NotNull
    @NotBlank
    @field:JsonProperty("content")
    @Column(name = "content", nullable = false)
    open var content: String = ""

    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATE_STRING_FORMAT)
    @field:JsonProperty("scheduled_at")
    @Column(name = "scheduled_at", nullable = false)
    open var scheduledAt: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
}