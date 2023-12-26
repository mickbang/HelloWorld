package com.lq.helloworld.volley

class Request:Comparable<Request> {
    var requestParam:String? = ""
     var isCanceled = false
    constructor(requestParam: String?) {
        this.requestParam = requestParam
    }

    fun cancel() {
        isCanceled = true
    }

    override fun compareTo(other: Request): Int {
        return 0
    }
}