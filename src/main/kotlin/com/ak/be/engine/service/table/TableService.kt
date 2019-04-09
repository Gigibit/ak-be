package com.ak.be.engine.service.table

import com.ak.be.engine.db.repository.TableRepository
import com.ak.be.engine.service.table.model.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TableService {
    @Autowired
    private lateinit var tableRepository: TableRepository

    fun getTablesByRestaurantsId(restaurantId: Int): List<Table> {
        return tableRepository.findTablesByRestaurantId(restaurantId)
                .map { Table(it.id, it.title, it.numberOfPlaces) }

    }
}