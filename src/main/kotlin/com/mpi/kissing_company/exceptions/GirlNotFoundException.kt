package com.mpi.kissing_company.exceptions

internal class GirlNotFoundException(id: String) : RuntimeException("Could not find girl $id")