import React from 'react'
import { Button, Space, Tooltip, theme } from 'antd'
import { QuestionCircleOutlined } from '@ant-design/icons'
import Search from 'antd/es/input/Search'
import ProductTagConfigs from './ProductTagConfigs'
import AppActions from '@/constants/AppActions '

interface ProductTagSearchProps {
    onSearch: (term: string) => void
    showModal: () => void
    handleDelete: () => void
    selectedRowKeys: React.Key[]
}

const ProductTagSearchComponent: React.FC<ProductTagSearchProps> = ({
    onSearch,
    showModal,
    handleDelete,
    selectedRowKeys,
}) => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken()

    return (
        <div
            className='mb-5'
            style={{
                padding: 24,
                minHeight: 190,
                background: colorBgContainer,
                borderRadius: borderRadiusLG,
            }}
        >
            <div>
                <h3 className='text-2xl font-bold'>{AppActions.SEARCH}</h3>
                <div className='flex px-5 pt-5 justify-center '>
                    <h3 className='text-xl font-bold me-5 '>
                        {ProductTagConfigs.searchFieldName}
                        <Tooltip title='Search by name tag ' className='ms-1'>
                            <QuestionCircleOutlined className='bg-blue-500 text-white rounded-full' />
                        </Tooltip>
                    </h3>

                    <Space style={{ marginBottom: 16, width: 820 }}>
                        <Search placeholder={ProductTagConfigs.searchFieldNameTooltip} onSearch={onSearch} />
                    </Space>

                    <Button
                        disabled={selectedRowKeys.length === 0}
                        onClick={handleDelete}
                        type='primary'
                        className='me-5'
                        danger
                    >
                        {AppActions.Delete}
                    </Button>

                    <Button
                        style={{ backgroundColor: '#374151', borderColor: '#374151', color: '#ffffff' }}
                        onClick={showModal}
                    >
                        {AppActions.ADD}
                    </Button>
                </div>
            </div>
        </div>
    )
}

export const ProductTagSearch = React.memo(ProductTagSearchComponent)
