import React, { useState, useRef, useEffect } from 'react'
import { Form, Input, Select, Checkbox, Button, Row, Col } from 'antd'
import { SearchOutlined, CaretUpOutlined } from '@ant-design/icons'
import type { ProductFilter } from '@/model/ProductFilter'
import { CategoryNameResponse } from '@/model/Category'
import CategoryConfigs from '../category/CategoryConfigs'
import useGetApi from '@/hooks/use-get-api'
import { ManufacturerResponseListName } from '@/model/Manufacturer'
import ManufactureConfigs from '../manufacturer/ManufactureConfigs'
import FetchUtils from '@/utils/FetchUtils'
import ProductConfigs from './ProductConfigs'
import { useNavigate } from 'react-router-dom'

const { Option } = Select
interface ProductFilterProps {
    onFilterChange: (filter: ProductFilter) => void
    handleNavigateBySku: (sku: string) => void
}

const ProductFilter: React.FC<ProductFilterProps> = ({ onFilterChange }) => {
    const [form] = Form.useForm()
    const [isOpen, setIsOpen] = useState(true)
    const contentRef = useRef<HTMLDivElement>(null)
    const [contentHeight, setContentHeight] = useState(0)
    const [sku, setSku] = useState<string>('')
    const navigate = useNavigate()
    const { data: categories } = useGetApi<CategoryNameResponse[]>(
        CategoryConfigs.resourceUrlListName,
        CategoryConfigs.resourceKey,
    )

    const { data: manufacturers } = useGetApi<ManufacturerResponseListName[]>(
        ManufactureConfigs.resourceUrlListName,
        ManufactureConfigs.resourceKey,
    )

    useEffect(() => {
        if (contentRef.current) {
            setContentHeight(contentRef.current.scrollHeight)
        }
    }, [])

    const toggleCollapse = () => {
        setIsOpen(!isOpen)
    }

    const onFinish = (values: ProductFilter) => {
        console.log('Form values:', values)
        onFilterChange(values)
    }

    const onFinishBySku = async () => {
        const productId = await FetchUtils.getByIdString<number>(ProductConfigs.resourceUrlBySku, sku || '-------')
        if (productId !== -1) {
            navigate(`/admin/products/${productId}`)
        }
    }

    return (
        <div className='mb-5 bg-white rounded-lg shadow-md overflow-hidden'>
            <div className='flex justify-between items-center p-4 cursor-pointer' onClick={toggleCollapse}>
                <span className='text-lg font-medium'>Search</span>
                <CaretUpOutlined rotate={isOpen ? 0 : 180} className='text-lg transition-transform duration-300' />
            </div>
            <div
                style={{
                    maxHeight: isOpen ? `${contentHeight}px` : '0px',
                    overflow: 'hidden',
                    transition: 'max-height 0.3s ease-out',
                }}
            >
                <div ref={contentRef} className='p-4'>
                    <Form form={form} layout='vertical' onFinish={onFinish}>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item label='Product name' name='name'>
                                    <Input />
                                </Form.Item>
                                <Form.Item label='Category' name='categoryId'>
                                    <Select defaultValue=''>
                                        <Option value=''>All</Option>
                                        {categories?.map((category: CategoryNameResponse) => (
                                            <Option key={category.id} value={category.id}>
                                                {category.name}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item name='searchSubCategory' valuePropName='checked'>
                                    <Checkbox>Search subcategories</Checkbox>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label='Manufacturer' name='manufacturerId'>
                                    <Select defaultValue=''>
                                        <Option value=''>All</Option>
                                        {manufacturers?.map((manufacturer: ManufacturerResponseListName) => (
                                            <Option key={manufacturer.id} value={manufacturer.id}>
                                                {manufacturer.manufacturerName}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item label='Published' name='published'>
                                    <Select defaultValue='All'>
                                        <Option value=''>All</Option>
                                        <Option value='true'>Published only</Option>
                                        <Option value='false'>Unpublished only</Option>
                                    </Select>
                                </Form.Item>
                                <Form.Item label='Go directly to product SKU' name='sku'>
                                    <Input.Group compact>
                                        <Input
                                            onChange={(e) => setSku(e.target.value)}
                                            style={{ width: 'calc(100% - 50px)' }}
                                        />
                                        <Button onClick={onFinishBySku} type='primary'>
                                            Go
                                        </Button>
                                    </Input.Group>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Form.Item>
                            <Row justify='center'>
                                <Col>
                                    <Button type='primary' htmlType='submit' icon={<SearchOutlined />}>
                                        Search
                                    </Button>
                                </Col>
                            </Row>
                        </Form.Item>
                    </Form>
                </div>
            </div>
        </div>
    )
}

export default ProductFilter
