package com.mpi.kissing_company.exceptions

internal class UserNotFoundException(username: String) : RuntimeException("Could not find user $username")