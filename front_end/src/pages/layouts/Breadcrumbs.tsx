import React from 'react'
import { Breadcrumb, Col, Row } from 'antd'
import { NavLink, useLocation } from 'react-router-dom'

const capitalizeChar = (str: string): string => {
    return str.charAt(0).toUpperCase() + str.slice(1)
}

const Breadcrumbs: React.FC = () => {
    const location = useLocation()
    const pathSnippets = location.pathname.split('/').filter((i) => i)
    const hiddenBreadcrumb = ['admin/specification-attributes/specification-attributes-group']
    const shouldDisableBreadcrumb = hiddenBreadcrumb.some((path) => location.pathname.includes(path))
    if (shouldDisableBreadcrumb) return null

    const breadcrumbItems = pathSnippets.map((snippet, index) => {
        const url = `/${pathSnippets.slice(0, index + 1).join('/')}`
        const title = capitalizeChar(snippet.replace(/-/g, ' '))
        const isLastItem = index === pathSnippets.length - 1

        return {
            key: url,
            title: isLastItem ? title : <NavLink to={url}>{title}</NavLink>,
        }
    })

    return (
        <Row>
            <Col className='flex items-center gap-2 p-4'>
                <Breadcrumb items={breadcrumbItems} />
            </Col>
        </Row>
    )
}

export { Breadcrumbs }
