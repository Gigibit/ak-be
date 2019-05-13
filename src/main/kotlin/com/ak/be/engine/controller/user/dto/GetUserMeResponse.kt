package com.ak.be.engine.controller.user.dto

import com.ak.be.engine.controller.core.dto.BaseResponse

data class GetUserMeResponse(val context: UserContextDto) : BaseResponse()
