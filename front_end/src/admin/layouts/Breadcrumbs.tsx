import React from 'react'
import { Breadcrumb, Col, Row } from 'antd'
import { NavLink, useLocation } from 'react-router-dom'

const capitalizeChar = (str: string): string => {
    return str.charAt(0).toUpperCase() + str.slice(1)
}

const Breadcrumbs: React.FC = () => {
    const location = useLocation()
    const pathSnippets = location.pathname.split('/').filter((i) => i)
    const breadcrumbItems = pathSnippets.map((snippet, index) => {
        const url = `/${pathSnippets.slice(0, index + 1).join('/')}`
        const title = snippet.replace(/-/g, ' ')
        const isLastItem = index === pathSnippets.length - 1

        return {
            key: url,
            title: isLastItem ? capitalizeChar(title) : <NavLink to={url}>{capitalizeChar(title)}</NavLink>,
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
