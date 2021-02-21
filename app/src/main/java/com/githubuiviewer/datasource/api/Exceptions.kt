package com.githubuiviewer.datasource.api

import java.lang.Exception

class DataLoadingException(message: String): Exception(message) // other

class UnauthorizedException(message: String): Exception(message) //401

class NotFoundException(message: String): Exception(message) //404