import AppActions from '@/constants/AppActions'
import ManagerPath from '@/constants/ManagerPath'
import { DeleteOutlined, PlusOutlined, SearchOutlined } from '@ant-design/icons'
import { Button, Modal } from 'antd'
import Input from 'antd/es/input/Input'
import { useState } from 'react'
import { Link } from 'react-router-dom'

interface ProductAttributeSearchProps {
    onSearch?: (filter: { name: string }) => void
    handleDelete?: () => void
    selectedRowKeys?: React.Key[]
}

export default function ProductAttributeSearch({
    onSearch,
    selectedRowKeys = [],
    handleDelete,
}: Readonly<ProductAttributeSearchProps>) {
    const [name, setName] = useState<string>('')

    const handleSearch = () => {
        if (onSearch) {
            onSearch({ name })
        }
    }

    const onDelete = () => {
        Modal.confirm({
            title: 'Do you want to delete Product Attribute?',
            okText: 'Delete',
            cancelText: 'Cancel',
            onOk: handleDelete,
        })
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <div>
                <h3 className='text-xl font-bold'>{AppActions.SEARCH}</h3>
                <div className='flex px-5 pt-5 justify-between'>
                    <div className='flex gap-4 flex-wrap'>
                        <Input
                            className='w-56'
                            size='large'
                            placeholder='Product attribute name'
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <Button
                            size='large'
                            type='primary'
                            icon={<SearchOutlined />}
                            iconPosition='end'
                            onClick={handleSearch}
                        >
                            {AppActions.SEARCH}
                        </Button>
                    </div>
                    <div className=''>
                        <Button
                            disabled={selectedRowKeys?.length === 0}
                            onClick={onDelete}
                            type='primary'
                            className='me-5'
                            danger
                            size='large'
                            icon={<DeleteOutlined />}
                        >
                            {AppActions.DELETE}
                        </Button>
                        <Link to={ManagerPath.PRODUCT_ATTRIBUTE_ADD}>
                            <Button className='default-btn-color' icon={<PlusOutlined />} size='large'>
                                {AppActions.ADD}
                            </Button>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}
