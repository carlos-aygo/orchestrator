package edu.eci.repositories

import edu.eci.models.SendEmail
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface SendEmailRepository : CrudRepository<SendEmail, Long>