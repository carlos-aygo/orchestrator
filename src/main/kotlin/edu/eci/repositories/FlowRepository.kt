package edu.eci.repositories

import edu.eci.models.Flow
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface FlowRepository: CrudRepository<Flow, Long>