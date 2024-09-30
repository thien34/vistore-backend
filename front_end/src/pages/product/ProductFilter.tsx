import React, { useState, useRef, useEffect } from 'react'
import { Form, Input, Select, Checkbox, Button, Row, Col, Space } from 'antd'
import { SearchOutlined, CaretUpOutlined } from '@ant-design/icons'
import { useNavigate } from 'react-router-dom'
import type { ProductFilter } from '@/model/ProductFilter'
import useGetApi from '@/hooks/use-get-api'
import { ManufacturerResponseListName } from '@/model/Manufacturer'
import ManufactureConfigs from '../manufacturer/ManufactureConfigs'
import FetchUtils from '@/utils/FetchUtils'
import ProductConfigs from './ProductConfigs'
import useCategoryCreateViewModel from '../category/CategoryCreate.vm'
import ManagerPath from '@/constants/ManagerPath'

const { Option } = Select

interface ProductFilterProps {
    onFilterChange: (filter: ProductFilter) => void
}

const ProductFilter: React.FC<ProductFilterProps> = ({ onFilterChange }) => {
    const [form] = Form.useForm()
    const [isOpen, setIsOpen] = useState(true)
    const contentRef = useRef<HTMLDivElement>(null)
    const [sku, setSku] = useState('')
    const navigate = useNavigate()

    const { categoryOptions } = useCategoryCreateViewModel()

    const { data: manufacturers } = useGetApi<ManufacturerResponseListName[]>(
        ManufactureConfigs.resourceUrlListName,
        ManufactureConfigs.resourceKey,
    )

    useEffect(() => {
        if (contentRef.current) {
            contentRef.current.style.maxHeight = isOpen ? `${contentRef.current.scrollHeight}px` : '0px'
        }
    }, [isOpen])

    const toggleCollapse = () => setIsOpen((prev) => !prev)

    const handleFinish = (values: ProductFilter) => onFilterChange(values)

    const handleNavigateBySku = async () => {
        if (sku.trim()) {
            const productId = await FetchUtils.getByIdString<number>(ProductConfigs.resourceUrlBySku, sku)
            if (productId !== -1) {
                navigate(`${ManagerPath.PRODUCT_EDIT.replace(':productId', String(productId))}`)
            }
        }
    }

    return (
        <div className='mb-5 bg-white rounded-lg shadow-md overflow-hidden'>
            <div className='flex justify-between items-center p-6 cursor-pointer' onClick={toggleCollapse}>
                <h3 className='text-xl font-bold'>Search</h3>
                <CaretUpOutlined rotate={isOpen ? 0 : 180} className='text-lg transition-transform duration-300' />
            </div>
            <div ref={contentRef} className='transition-[max-height] duration-300 ease-out overflow-hidden'>
                <div className='px-12'>
                    <Form
                        form={form}
                        layout='vertical'
                        size='large'
                        onFinish={handleFinish}
                        initialValues={{ published: '' }}
                    >
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item label='Product name' name='name'>
                                    <Input />
                                </Form.Item>
                                <Form.Item label='Category' name='categoryId'>
                                    <Select
                                        showSearch
                                        filterOption={(input, option) =>
                                            (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
                                        }
                                        options={[{ label: 'All', value: '' }, ...categoryOptions]}
                                    />
                                </Form.Item>
                                <Form.Item name='searchSubCategory' valuePropName='checked'>
                                    <Checkbox>Search subcategories</Checkbox>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item label='Manufacturer' name='manufacturerId'>
                                    <Select>
                                        <Option value=''>All</Option>
                                        {manufacturers?.map(({ id, manufacturerName }) => (
                                            <Option key={id} value={id}>
                                                {manufacturerName}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item label='Published' name='published'>
                                    <Select>
                                        <Option value=''>All</Option>
                                        <Option value='true'>Published only</Option>
                                        <Option value='false'>Unpublished only</Option>
                                    </Select>
                                </Form.Item>
                                <Form.Item label='Go directly to product SKU' name='sku'>
                                    <Space.Compact block>
                                        <Input value={sku} onChange={(e) => setSku(e.target.value)} />
                                        <Button onClick={handleNavigateBySku} type='primary'>
                                            Go
                                        </Button>
                                    </Space.Compact>
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
