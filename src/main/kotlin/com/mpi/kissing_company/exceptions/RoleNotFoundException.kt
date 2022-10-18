package com.mpi.kissing_company.exceptions

internal class RoleNotFoundException(id: Long) : RuntimeException("Could not find user $id")