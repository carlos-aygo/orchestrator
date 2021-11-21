package edu.eci.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
@Entity
@Table(name = "flows")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
open class Flow {

    @Id
    @field:JsonProperty("id")
    @SequenceGenerator(name = "flows_id_seq", sequenceName = "flows_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flows_id_seq")
    open var id: Long = 0

    @field:JsonProperty("name")
    @Column(name = "name", nullable = false)
    @NotNull
    @NotBlank
    open var name: String = ""

    @NotNull
    @NotBlank
    @field:JsonProperty("flow")
    @Column(name = "flow", nullable = false)
    open var flow: String = ""

    @field:JsonProperty("conditions")
    @Type(type = "jsonb")
    @Column(name = "conditions", nullable = false)
    open var conditions: List<String> = emptyList()

    @field:JsonProperty("metadata")
    @Column(name = "metadata", nullable = false)
    @Type(type = "jsonb")
    open var metadata: Map<String, Any> = emptyMap()
}