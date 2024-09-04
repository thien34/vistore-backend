import { Form, Input, Button, Space, Modal, Table, Empty, Collapse, Checkbox, InputNumber } from 'antd'
import { CheckCircleOutlined, DeleteOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import useProductAttributeCreate from '@/pages/product-attributes/ProductAttributeCreate.vm.ts'
import TextArea from 'antd/es/input/TextArea'

const pageSize = 6

export default function ProductAttributeCreate() {
    const {
        handlePageChange,
        handleFinish,
        handleAddValue,
        handleRemoveValue,
        handleEditValue,
        isModalOpen,
        handleCancelModal,
        setIsModalOpen,
        values,
        newValue,
        loading,
        current,
        form,
        formAdd,
        setNewValue,
        handleInputChange,
    } = useProductAttributeCreate()

    const columns = [
        { title: 'Name', dataIndex: 'name', key: 'name' },
        {
            title: 'Price Adjustment',
            dataIndex: 'priceAdjustment',
            key: 'priceAdjustment',
            render: (text: number, record: PredefinedProductAttributeValueRequest) =>
                `${text} ${record.priceAdjustmentUsePercentage ? '%' : '$'}`,
        },
        {
            title: 'Price Adjustment Use Percentage',
            dataIndex: 'priceAdjustmentUsePercentage',
            key: 'priceAdjustmentUsePercentage',
            render: (text: boolean) => (text ? '✔' : '✘'),
        },
        { title: 'Weight Adjustment', dataIndex: 'weightAdjustment', key: 'weightAdjustment' },
        { title: 'Cost', dataIndex: 'cost', key: 'cost' },
        {
            title: 'Pre-selected',
            dataIndex: 'isPreSelected',
            key: 'isPreSelected',
            render: (text: boolean) => (text ? '✔' : '✘'),
        },
        { title: 'Display Order', dataIndex: 'displayOrder', key: 'displayOrder' },
        {
            title: 'Action',
            key: 'action',
            render: (record: PredefinedProductAttributeValueRequest) => (
                <Space size='middle'>
                    <Button
                        type='link'
                        style={{
                            backgroundColor: '#4682B4',
                            color: '#FFFFFF',
                        }}
                        icon={<EditOutlined />}
                        onClick={() => handleEditValue(record)}
                    >
                        Edit
                    </Button>

                    <Button
                        type='link'
                        style={{
                            color: '#FFFFFF',
                            backgroundColor: '#FF0000',
                            border: 'none',
                        }}
                        icon={<DeleteOutlined />}
                        onClick={() => handleRemoveValue(record)}
                    >
                        Remove
                    </Button>
                </Space>
            ),
        },
    ]

    const showConfirmModal = () => {
        Modal.confirm({
            title: 'Confirm Creation',
            content: 'Do you want to create Product Attribute?',
            okText: 'Yes',
            cancelText: 'No',
            onOk: () => handleFinish(form.getFieldsValue()),
        })
    }

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <Form id='myForm' form={form} onFinish={handleFinish} layout='vertical' style={{ width: '100%' }}>
                <Collapse defaultActiveKey={['1']} style={{ height: '100%' }}>
                    <Collapse.Panel header='Attribute Information' key='1' style={{ height: '100%' }}>
                        <div
                            style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}
                        >
                            <div style={{ width: '66.67%' }}>
                                <Form.Item
                                    name='name'
                                    label='Name'
                                    rules={[{ required: true, message: 'Please enter the attribute name' }]}
                                >
                                    <Input size='large' placeholder='Enter attribute name' style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item name='description' label='Description'>
                                    <TextArea
                                        size='large'
                                        rows={3}
                                        placeholder='Enter description'
                                        style={{ width: '100%' }}
                                    />
                                </Form.Item>
                                <Form.Item className='mt-5' style={{ display: 'flex', justifyContent: 'center' }}>
                                    <Button
                                        type='primary'
                                        style={{ backgroundColor: '#34C759', color: '#FFFFFF' }}
                                        onClick={showConfirmModal}
                                        icon={<CheckCircleOutlined />}
                                        loading={loading}
                                    >
                                        Save
                                    </Button>
                                </Form.Item>
                            </div>
                        </div>
                    </Collapse.Panel>
                    <Collapse.Panel header='Predefined Values' key='2'>
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
                                    scroll={{ x: 1000 }}
                                />
                            )}
                        </Space>
                    </Collapse.Panel>
                </Collapse>
            </Form>

            <Modal
                title='Add Predefined Value'
                open={isModalOpen}
                centered
                onOk={formAdd.submit}
                onCancel={handleCancelModal}
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
                            addonAfter={newValue.priceAdjustmentUsePercentage ? '%' : '$'}
                            value={newValue.priceAdjustment || 0}
                            onChange={handleInputChange('priceAdjustment', 12, 2)}
                            placeholder='Enter price adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item label={<span style={{ fontWeight: 'bold' }}>Price Adjustment Use Percentage</span>}>
                        <Checkbox
                            checked={newValue.priceAdjustmentUsePercentage}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    priceAdjustmentUsePercentage: e.target.checked,
                                })
                            }
                        />
                    </Form.Item>
                    <Form.Item
                        name='weightAdjustment'
                        label={<span style={{ fontWeight: 'bold' }}>Weight Adjustment</span>}
                    >
                        <Input
                            addonAfter={'$'}
                            value={newValue.weightAdjustment || 0}
                            onChange={handleInputChange('weightAdjustment', 12, 2)}
                            placeholder='Enter weight adjustment'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item name='cost' label={<span style={{ fontWeight: 'bold' }}>Cost</span>}>
                        <Input
                            addonAfter={'$'}
                            value={newValue.cost || 0}
                            onChange={handleInputChange('cost', 12, 2)}
                            placeholder='Enter cost'
                            type='number'
                        />
                    </Form.Item>
                    <Form.Item label={<span style={{ fontWeight: 'bold' }}>Is Pre-selected</span>}>
                        <Checkbox
                            checked={newValue.isPreSelected}
                            onChange={(e) =>
                                setNewValue({
                                    ...newValue,
                                    isPreSelected: e.target.checked,
                                })
                            }
                        />
                    </Form.Item>
                    <Form.Item name='displayOrder' label={<span style={{ fontWeight: 'bold' }}>Display Order</span>}>
                        <InputNumber
                            value={newValue.displayOrder || 0}
                            onChange={(value) =>
                                setNewValue({
                                    ...newValue,
                                    displayOrder: value ?? 0,
                                })
                            }
                            placeholder='Enter display order'
                            style={{ width: '100%' }}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}
