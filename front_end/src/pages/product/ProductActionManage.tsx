import React, { useEffect } from 'react'
import { Button, Dropdown } from 'antd'
import { DownloadOutlined, ExportOutlined, ImportOutlined, DeleteOutlined, PlusOutlined } from '@ant-design/icons'
import type { MenuProps } from 'antd'
import { useNavigate } from 'react-router-dom'
import ManagerPath from '@/constants/ManagerPath'
import { resetProductState } from '@/slice/productSlice'
import { useDispatch } from 'react-redux'

const ProductActionManage: React.FC = () => {
    const navigate = useNavigate()
    const exportItems: MenuProps['items'] = [
        { key: '1', label: 'Export  1' },
        { key: '2', label: 'Export  2' },
    ]
    const dispatch = useDispatch()
    useEffect(() => {
        dispatch(resetProductState())
    }, [dispatch])

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-20'>
            <div className='flex justify-end space-x-2'>
                <div className='flex space-x-2  p-2'>
                    <Button
                        onClick={() => navigate(ManagerPath.PRODUCT_CREATE)}
                        type='primary'
                        icon={<PlusOutlined />}
                        className=' hover:bg-blue-500'
                    >
                        Add new
                    </Button>
                    <Button
                        type='primary'
                        icon={<DownloadOutlined />}
                        className='bg-purple-600 hover:bg-purple-700 text-white'
                    >
                        Download catalog as PDF
                    </Button>
                    <Dropdown menu={{ items: exportItems }} placement='bottomLeft'>
                        <Button
                            type='primary'
                            icon={<ExportOutlined />}
                            className='bg-green-500 hover:bg-green-600 text-white'
                        >
                            Export
                        </Button>
                    </Dropdown>
                    <Button
                        type='primary'
                        icon={<ImportOutlined />}
                        className='bg-green-500 hover:bg-green-600 text-white'
                    >
                        Import
                    </Button>
                    <Button danger type='primary' icon={<DeleteOutlined />}>
                        Delete (selected)
                    </Button>
                </div>
            </div>
        </div>
    )
}

export default ProductActionManage
