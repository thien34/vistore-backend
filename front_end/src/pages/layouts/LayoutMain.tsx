import React, { useState } from 'react'
import { DesktopOutlined, FileOutlined, PieChartOutlined, TeamOutlined, UserOutlined } from '@ant-design/icons'
import type { MenuProps } from 'antd'
import { Layout, Menu } from 'antd'
import { Outlet } from 'react-router-dom'
import { Breadcrumbs } from './Breadcrumbs'

const { Header, Content, Footer, Sider } = Layout

type MenuItem = Required<MenuProps>['items'][number]

function getItem(label: React.ReactNode, key: React.Key, icon?: React.ReactNode, children?: MenuItem[]): MenuItem {
    return {
        key,
        icon,
        children,
        label,
    } as MenuItem
}

const items: MenuItem[] = [
    getItem('Option 1', '1', <PieChartOutlined />),
    getItem('Option 2', '2', <DesktopOutlined />),
    getItem('User', 'sub1', <UserOutlined />, [getItem('Tom', '3'), getItem('Bill', '4'), getItem('Alex', '5')]),
    getItem('Team', 'sub2', <TeamOutlined />, [getItem('Team 1', '6'), getItem('Team 2', '8')]),
    getItem('Files', '9', <FileOutlined />),
]

const LayoutMain: React.FC = () => {
    const [collapsed, setCollapsed] = useState(false)

    return (
        <Layout className='min-h-screen'>
            <Sider className='!bg-[#fff]' collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <Menu theme='light' defaultSelectedKeys={['1']} mode='inline' items={items} />
            </Sider>

            <Layout>
                <Header className='bg-[#fff]' />
                <Content>
                    <div className='px-6'>
                        <Breadcrumbs />
                    </div>
                    <div className='px-8'>
                        <Outlet />
                    </div>
                </Content>
                <Footer className='text-center'>Ant Design ©{new Date().getFullYear()} Created by Ant UED</Footer>
            </Layout>
        </Layout>
    )
}

export default LayoutMain
