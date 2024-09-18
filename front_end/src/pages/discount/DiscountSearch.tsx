import { Button, Col, DatePicker, Form, Input, Row, Select } from 'antd'
import { useState } from 'react'
import dayjs from 'dayjs'
import DiscountConfigs from './DiscountConfigs'

export type Filter = {
    startDate?: string
    endDate?: string
    discountTypeId: string
    couponCode?: string
    discountName?: string
    isActive?: string
}

interface DiscountSearchProps {
    search: (filter: Filter) => void
}

export default function DiscountSearch({ search }: DiscountSearchProps) {
    const [filter, setFilter] = useState<Filter>({
        startDate: undefined,
        endDate: undefined,
        discountTypeId: 'All',
        couponCode: '',
        discountName: '',
        isActive: 'All',
    })

    const handleSearch = () => {
        search(filter)
    }

    return (
        <div className='bg-[#fff] rounded-lg shadow-md p-6 '>
            <Form layout='vertical'>
                <Row gutter={16}>
                    <Col span={8}>
                        <Form.Item label='Start Date'>
                            <DatePicker
                                showTime
                                placeholder='From'
                                className='w-full'
                                onChange={(_date, dateString) => {
                                    if (typeof dateString === 'string') {
                                        setFilter((prev) => ({
                                            ...prev,
                                            startDate: dateString ? dayjs(dateString).toISOString() : undefined,
                                        }))
                                    }
                                }}
                            />
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label='End Date'>
                            <DatePicker
                                showTime
                                placeholder='To'
                                className='w-full'
                                onChange={(_date, dateString) => {
                                    if (typeof dateString === 'string') {
                                        setFilter((prev) => ({
                                            ...prev,
                                            endDate: dateString ? dayjs(dateString).toISOString() : undefined,
                                        }))
                                    }
                                }}
                            />
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label='Discount Type'>
                            <Select
                                defaultValue='All'
                                className='w-full'
                                onChange={(value) => setFilter((prev) => ({ ...prev, discountTypeId: value }))}
                            >
                                <Select.Option value='All'>All</Select.Option>
                                <Select.Option value='ASSIGNED_TO_ORDER_TOTAL'>Assigned to order total</Select.Option>
                                <Select.Option value='ASSIGNED_TO_PRODUCTS'>Assigned to products</Select.Option>
                                <Select.Option value='ASSIGNED_TO_CATEGORIES'>Assigned to categories</Select.Option>
                                <Select.Option value='ASSIGNED_TO_MANUFACTURERS'>
                                    Assigned to manufacturers
                                </Select.Option>
                                <Select.Option value='ASSIGNED_TO_ORDER_SUBTOTAL'>
                                    Assigned to order subtotal
                                </Select.Option>
                            </Select>
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label='Coupon Code'>
                            <Input
                                placeholder='Enter coupon code'
                                onChange={(e) => setFilter((prev) => ({ ...prev, couponCode: e.target.value }))}
                            />
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label='Discount Name'>
                            <Input
                                placeholder='Enter discount name'
                                onChange={(e) => setFilter((prev) => ({ ...prev, discountName: e.target.value }))}
                            />
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label='Is Active'>
                            <Select
                                defaultValue='All'
                                className='w-full'
                                onChange={(value) => setFilter((prev) => ({ ...prev, isActive: value }))}
                            >
                                <Select.Option value='All'>All</Select.Option>
                                <Select.Option value='true'>Yes</Select.Option>
                                <Select.Option value='false'>No</Select.Option>
                            </Select>
                        </Form.Item>
                    </Col>
                    <Col span={24}>
                        <Button type='primary' onClick={handleSearch}>
                            {DiscountConfigs.searchTitle}
                        </Button>
                    </Col>
                </Row>
            </Form>
        </div>
    )
}
