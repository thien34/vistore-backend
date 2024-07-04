import React from 'react'

class NotifyUtils {
    static simple = (message: React.ReactNode) => {
        console.log(`<<<<<  message >>>>>`, message)
    }

    static simpleSuccess = (message: React.ReactNode) => {
        console.log(`<<<<<  message >>>>>`, message)
    }

    static simpleFailed = (message: React.ReactNode) => {
        console.log(`<<<<<  message >>>>>`, message)
    }
}

export default NotifyUtils
