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

        return (
            <Breadcrumb.Item key={url} {...(isLastItem && { className: 'ant-breadcrumb-link' })}>
                {' '}
                {isLastItem ? capitalizeChar(title) : <NavLink to={url}>{capitalizeChar(title)}</NavLink>}
            </Breadcrumb.Item>
        )
    })

    return (
        <>
            <Row>
                <Col className='flex items-center gap-2 p-4'>
                    <Breadcrumb>{breadcrumbItems}</Breadcrumb>
                </Col>
            </Row>
        </>
    )
}

export { Breadcrumbs }
