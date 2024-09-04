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
        const title = capitalizeChar(snippet.replace(/-/g, ' '))
        const isLastItem = index === pathSnippets.length - 1

        return {
            key: url,
            title: isLastItem ? title : <NavLink to={url}>{title}</NavLink>,
        }
    })

    const labelValue =
        pathSnippets.length > 0 ? capitalizeChar(pathSnippets[pathSnippets.length - 1].replace(/-/g, ' ')) : 'Home'

    return (
        <Row>
            <Col className='flex items-center gap-2 p-4'>
                <Breadcrumb items={breadcrumbItems} />
            </Col>
            {pathSnippets.length > 0 && (
                <Col span={24} className='flex items-center pl-4 pb-2 font-medium'>
                    <label>{labelValue}</label>
                </Col>
            )}
        </Row>
    )
}

export { Breadcrumbs }
