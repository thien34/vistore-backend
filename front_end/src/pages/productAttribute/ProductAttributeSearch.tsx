import { SearchOutlined } from '@ant-design/icons'
import { Button, Modal } from 'antd'
import Input from 'antd/es/input/Input'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import AppActions from '@/constants/AppActions .ts'

interface ProductAttributeSearchProps {
    onSearch?: (filter: { name: string; published: boolean | undefined }) => void
    handleDelete?: () => void
    selectedRowKeys?: React.Key[]
}

export default function ProductAttributeSearch({
    onSearch,
    selectedRowKeys = [],
    handleDelete,
}: Readonly<ProductAttributeSearchProps>) {
    const [name, setName] = useState<string>('')
    const [isOpenConfirm, setIsOpenConfirm] = useState(false)

    const handleSearch = () => {
        const payload: { name: string } = { name }
        if (onSearch) {
            onSearch(payload)
        }
    }

    const onDelete = () => {
        handleDelete()
        setIsOpenConfirm(false)
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Modal
                onCancel={() => setIsOpenConfirm(false)}
                footer={() => (
                    <div>
                        <Button onClick={onDelete} className='bg-[#CC3300] border-[#374151] text-white'>
                            Delete
                        </Button>
                        <Button type='default' onClick={() => setIsOpenConfirm(false)}>
                            Cancel
                        </Button>
                    </div>
                )}
                open={isOpenConfirm}
            >
                <div className={'py-10 font-bold text-[16px]'}>Do you want to delete Product Attribute?</div>
            </Modal>
            <div>
                <h3 className='text-xl font-bold'>{AppActions.SEARCH}</h3>
                <div className='flex px-5 pt-5 justify-between'>
                    <div className='flex gap-4 flex-wrap'>
                        <Input
                            className='w-56'
                            size='large'
                            placeholder='Product Attribute name'
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <Button
                            size='large'
                            type='primary'
                            icon={<SearchOutlined />}
                            iconPosition={'end'}
                            onClick={handleSearch}
                        >
                            {AppActions.SEARCH}
                        </Button>
                    </div>
                    <div className=''>
                        <Button
                            disabled={selectedRowKeys?.length === 0}
                            onClick={() => setIsOpenConfirm(true)}
                            type='primary'
                            className='me-5'
                            danger
                            size='large'
                        >
                            {AppActions.DELETE}
                        </Button>
                        <Link to='/admin/product-attributes/add'>
                            <Button className='bg-[#475569] text-white' size='large'>
                                {AppActions.ADD}
                            </Button>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}
