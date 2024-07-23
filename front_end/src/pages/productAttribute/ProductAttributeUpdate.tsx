import { useEffect } from 'react'
import { Form, Input, Button, Space, Modal, Table, Empty } from 'antd'
import {
    CheckCircleOutlined,
    DeleteOutlined,
    EditOutlined,
    ExclamationCircleOutlined,
    PlusOutlined,
} from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import useProductAttributeUpdate from '@/pages/productAttribute/ProductAttributeUpdate.vm.ts'

const pageSize = 5
export default function ProductAttributeUpdate() {

    const {
        productAttributeResponse,
        isLoading,
        current,
        handlePageChange,
        form,
        formAdd,
        values,
        setValues,
        newValue,
        setNewValue,
        isModalOpen,
        setIsModalOpen,
        setIsEdit,
        loading,
        setOpenConfirm,
        isOpenConfirm,
        gradientStyleEdit,
        handleEditValue,
        gradientStyleRemove,
        handleRemoveValue,
        handleFinish,
        gradientStyleSave,
        handleAddValue,
        handleInputChange,
        handleInputDisplayOrder,
    } = useProductAttributeUpdate()
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
                    <Button
                        type='link'
                        style={gradientStyleEdit}
                        icon={<EditOutlined />}
                        onClick={() => handleEditValue(record)}
                    >
                        Edit
                    </Button>
                    <Button
                        type='link'
                        style={gradientStyleRemove}
                        icon={<DeleteOutlined />}
                        onClick={() => handleRemoveValue(record)}
                    >
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
                            <Button type='default' key='cancel' onClick={() => setOpenConfirm(false)}>
                                Cancel
                            </Button>
                        </div>
                    )}
                    open={isOpenConfirm}
                    title={
                        <span>
                            <ExclamationCircleOutlined style={{ marginRight: 8 }} /> Confirm Action
                        </span>
                    }
                >
                    <p className='text-center'>Do you want to create Product Attribute?</p>
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
                        <Button
                            style={gradientStyleSave}
                            icon={<CheckCircleOutlined />}
                            onClick={() => setOpenConfirm(true)}
                            loading={loading}
                        >
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
                            value={newValue.priceAdjustment || 0}
                            onChange={handleInputChange('priceAdjustment')}
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
                            value={newValue.weightAdjustment || 0}
                            onChange={handleInputChange('weightAdjustment')}
                            placeholder='Enter weight adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item name='cost' label={<span style={{ fontWeight: 'bold' }}>Cost</span>}>
                        <Input
                            value={newValue.cost || 0}
                            onChange={handleInputChange('cost')}
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
                            value={newValue.displayOrder || 0}
                            onChange={handleInputDisplayOrder('displayOrder')}
                            placeholder='Enter display order'
                            type='number'
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}
