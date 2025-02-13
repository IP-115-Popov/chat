package com.eltex.data.mappers

import com.eltex.data.models.users.UserDTO
import com.eltex.domain.models.UserModel

object UserDTOToUserModelMapper {
    fun map(userDTO: UserDTO) = with(userDTO) {
        UserModel(
        _id = _id,
         active = active,
         name = name,
         nameInsensitive = nameInsensitive,
         status = status,
         username = username
        )
    }
}