import React, { useState } from 'react'
import {
    BranchesOutlined,
    ExperimentFilled,
    FrownFilled,
    FundFilled,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PieChartOutlined,
    ProductFilled,
    ProfileOutlined,
    QuestionCircleOutlined,
    SettingFilled,
    ShoppingCartOutlined,
    TagsOutlined,
} from '@ant-design/icons'
import type { MenuProps } from 'antd'
import { Button, Layout, Menu } from 'antd'
import { Link, Outlet } from 'react-router-dom'
import { Breadcrumbs } from './Breadcrumbs'
import ManagerPath from '@/constants/ManagerPath'

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

const siderStyle: React.CSSProperties = {
    overflow: 'auto',
    height: '100vh',
    position: 'fixed',
    insetInlineStart: 0,
    top: 0,
    bottom: 0,
    scrollbarWidth: 'none',
    scrollbarColor: 'unset',
}

const items: MenuItem[] = [
    getItem(<Link to={ManagerPath.DASHBOARD}>Dashboard</Link>, '1', <PieChartOutlined />),
    getItem(<Link to={ManagerPath.PRODUCT}>Product</Link>, '2', <ProductFilled />),
    getItem('Catalog', 'sub1', <ProfileOutlined />, [
        getItem(<Link to={ManagerPath.CATEGORY}>Categories</Link>, '3'),
        getItem(<Link to={ManagerPath.MANUFACTURE}>Manufacturers</Link>, '4'),
        getItem('Product reviews', '5'),
        getItem(<Link to={ManagerPath.PRODUCT_TAG}>Product tags</Link>, '6'),
    ]),
    getItem('Attributes', 'sub2', <ExperimentFilled />, [
        getItem(<Link to={ManagerPath.PRODUCT_ATTRIBUTE}>Product attributes</Link>, '7'),
        getItem(<Link to={ManagerPath.SPECIFICATION_ATTRIBUTE}>Specification attributes</Link>, '8'),
    ]),
    getItem('Sales', '9', <ShoppingCartOutlined />, [
        getItem('Orders', '10'),
        getItem('Shipments', '11'),
        getItem('Return requests', '12'),
        getItem('Recurring payments', '13'),
        getItem('Carts & Wishlists', '14'),
    ]),
    getItem('Customers', '15', <FrownFilled />, [
        getItem('Customers', '16'),
        getItem('Customer roles', '17'),
        getItem('Online customers', '18'),
        getItem('Activity log', '19'),
        getItem('Activity Types', '20'),
    ]),
    getItem('Promotions', '21', <TagsOutlined />, [
        getItem(<Link to={ManagerPath.DISCOUNT}>Discounts</Link>, '22'),
        getItem('Affiliates', '23'),
        getItem('Newsletter subscribers', '24'),
        getItem('Campaigns', '25'),
    ]),
    getItem('Configurations', '26', <SettingFilled />),
    getItem('System', '27', <BranchesOutlined />),
    getItem('Reports', '28', <FundFilled />),
    getItem('Help', '29', <QuestionCircleOutlined />),
]

const LayoutMain: React.FC = () => {
    const [collapsed, setCollapsed] = useState(false)

    const mainStyle: React.CSSProperties = {
        marginLeft: collapsed ? 80 : 240,
    }

    const imgStyle: React.CSSProperties = {
        display: collapsed ? 'none' : 'block',
    }

    return (
        <Layout className='min-h-screen transition-all duration-300' hasSider style={mainStyle}>
            <Sider
                style={siderStyle}
                theme='light'
                collapsible
                collapsed={collapsed}
                onCollapse={(value) => setCollapsed(value)}
                breakpoint='lg'
                width={240}
            >
                <div className='h-16 bg-white flex items-center justify-center sticky top-0 z-10 '>
                    <img src='/img/ViStore.png' alt='Vítore' className='h-12' />
                    <div style={imgStyle} className='text-lg font-bold pr-3 '>
                        Vítore
                    </div>
                </div>
                <Menu className='text-base' defaultSelectedKeys={['1']} mode='inline' items={items} />
            </Sider>

            <Layout>
                <Header className='p-0 bg-white sticky top-0 z-10 w-full flex items-center'>
                    <Button
                        type='text'
                        icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                        onClick={() => setCollapsed(!collapsed)}
                        className='text-[16px] !w-16 !h-16'
                    />
                </Header>
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
