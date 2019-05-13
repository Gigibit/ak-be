package com.ak.be.engine.controller.user.dto

import com.ak.be.engine.controller.core.dto.BaseResponse
import com.ak.be.engine.controller.restaurant.dto.CompleteUserDto

data class GetUserByIdResponse(val user: CompleteUserDto) : BaseResponse()
