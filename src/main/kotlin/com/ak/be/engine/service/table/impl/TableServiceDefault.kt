package com.ak.be.engine.service.table.impl

import com.ak.be.engine.db.entity.AkTableEntity
import com.ak.be.engine.db.repository.RestaurantRepository
import com.ak.be.engine.db.repository.TableRepository
import com.ak.be.engine.service.table.TableService
import com.ak.be.engine.service.table.model.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TableServiceDefault(@Autowired val tableRepository: TableRepository, @Autowired val restaurantRepository: RestaurantRepository) : TableService {

    override fun getTablesByRestaurantsId(restaurantId: Int): List<Table> {
        return tableRepository.findTablesByRestaurantId(restaurantId)
                .map(fromEntityToModel)
    }

    @Transactional
    override fun createTablesForRestaurantsId(restaurantId: Int, table: Table): Table {
        val restaurantFound = restaurantRepository.findById(restaurantId)
        if (!restaurantFound.isPresent) {
            throw IllegalArgumentException("restaurantId not found")
        }
        val akTableEntity = AkTableEntity()
        akTableEntity.title = table.title
        akTableEntity.numberOfPlaces = table.numberOfPlaces
        akTableEntity.akRestaurantByRestaurantId = restaurantFound.get()
        val save: AkTableEntity = tableRepository.save(akTableEntity)
        return (fromEntityToModel(save))
    }

    val fromEntityToModel = { entity: AkTableEntity -> Table(entity.id, entity.title, entity.numberOfPlaces) }
}