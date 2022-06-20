package com.mpi.kissing_company.exceptions

internal class RoleNotFoundException(id: Int) : RuntimeException("Could not find user $id")