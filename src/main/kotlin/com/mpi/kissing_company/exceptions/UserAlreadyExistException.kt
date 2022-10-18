package com.mpi.kissing_company.exceptions

internal class UserAlreadyExistException(username: String?) : RuntimeException("User $username already exists")