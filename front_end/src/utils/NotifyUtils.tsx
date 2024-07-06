import React from 'react'

class NotifyUtils {
    static readonly simple = (message: React.ReactNode) => {
        console.log(`<<<<<  message >>>>>`, message)
    }

    static readonly simpleSuccess = (message: React.ReactNode) => {
        console.log(`<<<<<  message >>>>>`, message)
    }

    static readonly simpleFailed = (message: React.ReactNode) => {
        console.log(`<<<<<  message >>>>>`, message)
    }
}

export default NotifyUtils
