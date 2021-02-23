package com.githubuiviewer.datasource.api

import java.io.IOException

class DataLoadingException(message: String): IOException(message) // other

class UnauthorizedException(message: String): IOException(message) //401

class NotFoundException(message: String): IOException(message) //404