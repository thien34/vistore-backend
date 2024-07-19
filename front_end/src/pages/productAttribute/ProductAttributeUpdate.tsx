import { useEffect, useState } from 'react'
import { Form, Input, Button, Space, Modal, Table, Empty, message } from 'antd'
import { PlusOutlined } from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import useProductAttributeUpdate from '@/pages/productAttribute/ProductAttributeUpdate.vm.ts'

const pageSize = 5

export default function ProductAttributeUpdate() {
    const [form] = Form.useForm()
    const [formAdd] = Form.useForm()
    const [values, setValues] = useState<PredefinedProductAttributeValueRequest[]>([])
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [isEdit, setIsEdit] = useState(false)
    const [newValue, setNewValue] = useState<PredefinedProductAttributeValueRequest>({
        id: 1,
        name: '',
        priceAdjustment: 0,
        priceAdjustmentUsePercentage: false,
        weightAdjustment: 0,
        cost: 0,
        isPreSelected: false,
        displayOrder: 0,
    })
    const [isOpenConfirm, setOpenConfirm] = useState(false)
    const [loading, setLoading] = useState(false)
    const [current, setCurrent] = useState(1)
    const { productAttributeResponse, isLoading, onFinish } = useProductAttributeUpdate()

    useEffect(() => {
        console.log('productAttributeResponse: ', productAttributeResponse)
        if (productAttributeResponse) {
            form.setFieldsValue({
                name: productAttributeResponse.name,
                description: productAttributeResponse.description,
            })
            setValues(productAttributeResponse.values)
        }
    }, [form, productAttributeResponse])

    const getNewId = (arr: PredefinedProductAttributeValueRequest[]) => {
        const maxId = Math.max(...arr.map((item) => item.id))
        return arr.length === 0 ? 0 : maxId + 1
    }

    const handleAddValue = () => {
        if (isEdit) {
            formAdd.resetFields()
            const index = values.findIndex((item) => item.id === newValue.id)
            const newArr = values.filter((item) => item.id !== newValue.id)
            newArr.splice(index, 0, newValue)
            setValues(newArr)
            setIsModalOpen(false)
            setIsEdit(false)
        } else {
            formAdd.resetFields()
            newValue.id = getNewId(values)
            setValues([...values, newValue])
            setNewValue({
                id: getNewId(values),
                name: '',
                priceAdjustment: 0,
                priceAdjustmentUsePercentage: false,
                weightAdjustment: 0,
                cost: 0,
                isPreSelected: false,
                displayOrder: 0,
            })
            setIsModalOpen(false)
        }
    }

    const handleRemoveValue = (value: PredefinedProductAttributeValueRequest) => {
        setValues(values.filter((v) => v.id !== value.id))
    }

    const handleEditValue = (value: PredefinedProductAttributeValueRequest) => {
        setIsModalOpen(true)
        formAdd.setFieldsValue(value)
        setNewValue(value)
        setIsEdit(true)
    }

    const handleFinish = async (formValues: { name: string; description: string }) => {
        setLoading(true)
        setOpenConfirm(false)
        try {
            form.resetFields()
            setValues([])

            const data = {
                name: formValues.name,
                description: formValues.description,
                values: values,
            }

            console.log('data: ', data)
            await onFinish(data)
        } catch (error) {
            console.error('Error submitting form:', error)
            message.error('Failed to submit form')
        } finally {
            setLoading(false)
        }
    }

    const handlePageChange = (page: number) => {
        setCurrent(page)
    }

    const columns = [
        { title: 'Name', dataIndex: 'name', key: 'name' },
        { title: 'Price Adjustment', dataIndex: 'priceAdjustment', key: 'priceAdjustment' },
        {
            title: 'Price Adjustment Use Percentage',
            dataIndex: 'priceAdjustmentUsePercentage',
            key: 'priceAdjustmentUsePercentage',
            render: (text: boolean) => (text ? 'Yes' : 'No'),
        },
        { title: 'Weight Adjustment', dataIndex: 'weightAdjustment', key: 'weightAdjustment' },
        { title: 'Cost', dataIndex: 'cost', key: 'cost' },
        {
            title: 'Pre-selected',
            dataIndex: 'isPreSelected',
            key: 'isPreSelected',
            render: (text: boolean) => (text ? 'Yes' : 'No'),
        },
        { title: 'Display Order', dataIndex: 'displayOrder', key: 'displayOrder' },
        {
            title: 'Action',
            key: 'action',
            render: (record: PredefinedProductAttributeValueRequest) => (
                <Space size='middle'>
                    <Button type='link' onClick={() => handleEditValue(record)}>
                        Edit
                    </Button>
                    <Button type='link' onClick={() => handleRemoveValue(record)}>
                        Remove
                    </Button>
                </Space>
            ),
        },
    ]

    if (isLoading && !productAttributeResponse) {
        return <div>Loading...</div>
    }

    return (
        <div>
            <Form id='myform1' form={form} onFinish={handleFinish} layout='vertical'>
                <Modal
                    onCancel={() => setOpenConfirm(false)}
                    footer={() => (
                        <div>
                            <Button form='myform1' key='submit' type='primary' htmlType='submit'>
                                Update
                            </Button>
                            <Button type='default' onClick={() => setOpenConfirm(false)}>
                                Cancel
                            </Button>
                        </div>
                    )}
                    open={isOpenConfirm}
                >
                    <div className={'py-10 font-bold text-[16px]'}>Do you want to update Product Attribute?</div>
                </Modal>
                <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                    <Form.Item
                        name='name'
                        label='Name'
                        rules={[{ required: true, message: 'Please enter the attribute name' }]}
                    >
                        <Input placeholder='Enter attribute name' />
                    </Form.Item>
                    <Form.Item name='description' label='Description'>
                        <Input placeholder='Enter description' />
                    </Form.Item>
                </div>
                <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                    <Form.Item label='Predefined Values'>
                        <Space direction='vertical' style={{ width: '100%' }}>
                            <Button type='dashed' onClick={() => setIsModalOpen(true)} icon={<PlusOutlined />}>
                                Add New Value
                            </Button>
                            {values.length === 0 ? (
                                <Empty description='No data available in table' />
                            ) : (
                                <Table
                                    dataSource={values}
                                    columns={columns}
                                    pagination={{
                                        current,
                                        pageSize,
                                        total: values.length,
                                        onChange: (page) => handlePageChange(page),
                                    }}
                                    rowKey='id'
                                />
                            )}
                        </Space>
                    </Form.Item>
                    <Form.Item>
                        <Button type='primary' onClick={() => setOpenConfirm(true)} loading={loading}>
                            Save
                        </Button>
                    </Form.Item>
                </div>
            </Form>

            <Modal
                title='Add Predefined Value'
                open={isModalOpen}
                centered
                onOk={formAdd.submit}
                onCancel={() => {
                    setIsModalOpen(false)
                    setIsEdit(false)
                }}
            >
                <Form form={formAdd} layout='vertical' onFinish={handleAddValue}>
                    <Form.Item
                        name='name'
                        label={<span style={{ fontWeight: 'bold' }}>Name</span>}
                        rules={[{ required: true, message: 'Please enter the name' }]}
                    >
                        <Input
                            value={newValue.name}
                            onChange={(e) => setNewValue({ ...newValue, name: e.target.value })}
                            placeholder='Enter name'
                        />
                    </Form.Item>
                    <Form.Item
                        name='priceAdjustment'
                        label={<span style={{ fontWeight: 'bold' }}>Price Adjustment</span>}
                    >
                        <Input
                            value={newValue.priceAdjustment}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    priceAdjustment: parseFloat(e.target.value),
                                })
                            }
                            placeholder='Enter price adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item label={<span style={{ fontWeight: 'bold' }}>Price Adjustment Use Percentage</span>}>
                        <input
                            type='checkbox'
                            checked={newValue.priceAdjustmentUsePercentage}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    priceAdjustmentUsePercentage: e.target.checked,
                                })
                            }
                            style={{ marginLeft: 8 }}
                        />
                    </Form.Item>
                    <Form.Item
                        name='weightAdjustment'
                        label={<span style={{ fontWeight: 'bold' }}>Weight Adjustment</span>}
                    >
                        <Input
                            value={newValue.weightAdjustment}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    weightAdjustment: parseFloat(e.target.value),
                                })
                            }
                            placeholder='Enter weight adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item name='cost' label={<span style={{ fontWeight: 'bold' }}>Cost</span>}>
                        <Input
                            value={newValue.cost}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    cost: parseFloat(e.target.value),
                                })
                            }
                            placeholder='Enter cost'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item label={<span style={{ fontWeight: 'bold' }}>Is Pre-selected</span>}>
                        <input
                            type='checkbox'
                            checked={newValue.isPreSelected}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    isPreSelected: e.target.checked,
                                })
                            }
                            style={{ marginLeft: 8 }}
                        />
                    </Form.Item>
                    <Form.Item name='displayOrder' label={<span style={{ fontWeight: 'bold' }}>Display Order</span>}>
                        <Input
                            value={newValue.displayOrder}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    displayOrder: parseFloat(e.target.value),
                                })
                            }
                            placeholder='Enter display order'
                            type='number'
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}
