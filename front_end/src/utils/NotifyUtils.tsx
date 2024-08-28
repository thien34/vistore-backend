import React from 'react'
import { notification } from 'antd'

type NotificationType = 'success' | 'info' | 'warning' | 'error'
class NotifyUtils {
    static api = notification

    static readonly simple = (message: React.ReactNode, type: NotificationType = 'info') => {
        NotifyUtils.api[type]({
            message: 'Notification',
            description: message,
            showProgress: true,
            pauseOnHover: true,
            duration: 2.5,
        })
    }

    static readonly simpleSuccess = (message: React.ReactNode) => {
        NotifyUtils.simple(message, 'success')
    }

    static readonly simpleFailed = (message: React.ReactNode) => {
        NotifyUtils.simple(message, 'error')
    }

    static readonly simpleWarning = (message: React.ReactNode) => {
        NotifyUtils.simple(message, 'warning')
    }

    static readonly simpleInfo = (message: React.ReactNode) => {
        NotifyUtils.simple(message, 'info')
    }
}

export default NotifyUtils
