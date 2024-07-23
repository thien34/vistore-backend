import { Form, Input, Button, Space, Modal, Table, Empty } from 'antd'
import { CheckCircleOutlined, DeleteOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import useProductAttributeCreate from '@/pages/productAttribute/ProductAttributeCreate.vm.ts'

const pageSize = 5

export default function ProductAttribute() {
    const {
        handlePageChange,
        handleFinish,
        handleAddValue,
        handleRemoveValue,
        handleEditValue,
        isModalOpen,
        setIsModalOpen,
        values,
        newValue,
        isOpenConfirm,
        setOpenConfirm,
        loading,
        current,
        gradientStyleEdit,
        gradientStyleSave,
        gradientStyleRemove,
        form,
        formAdd,
        setNewValue,
        setIsEdit,
        handleInputChange,
    } = useProductAttributeCreate()
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

    return (
        <div>
            <Form id='myForm' form={form} onFinish={handleFinish} layout='vertical'>
                <Modal
                    onCancel={() => setOpenConfirm(false)}
                    footer={() => (
                        <div>
                            <Button style={{ margin: 8 }} form='myForm' key='submit' type='primary' htmlType='submit'>
                                Submit
                            </Button>
                            <Button type='default' onClick={() => setOpenConfirm(false)}>
                                Cancel
                            </Button>
                        </div>
                    )}
                    open={isOpenConfirm}
                >
                    <div className={'py-10 font-bold text-[16px]'}>Do you want to create Product Attribute?</div>
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
                            type='primary'
                            style={gradientStyleSave}
                            onClick={() => setOpenConfirm(true)}
                            icon={<CheckCircleOutlined />}
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
                            onChange={handleInputChange('priceAdjustment', 12, 2)}
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
                            onChange={handleInputChange('weightAdjustment', 12, 2)}
                            placeholder='Enter weight adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item name='cost' label={<span style={{ fontWeight: 'bold' }}>Cost</span>}>
                        <Input
                            value={newValue.cost || 0}
                            onChange={handleInputChange('cost', 12, 2)}
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
                            onChange={handleInputChange('displayOrder', 8, 0)}
                            placeholder='Enter display order'
                            type='number'
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}
