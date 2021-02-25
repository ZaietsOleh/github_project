package com.githubuiviewer.datasource.api

import java.io.IOException

class DataLoadingException(): IOException() // other

class UnauthorizedException(): IOException() //401

class NetworkException(): IOException()