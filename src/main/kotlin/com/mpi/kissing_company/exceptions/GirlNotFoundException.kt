package com.mpi.kissing_company.exceptions

internal class GirlNotFoundException(id: Long) : RuntimeException("Could not find girl $id")