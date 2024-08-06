import { useState, useEffect } from 'react'
import { Form, Input, Select, Button, Row, Col, Table, Space, Modal, Checkbox, message } from 'antd'
import { useNavigate, useParams } from 'react-router-dom'
import { DeleteOutlined, EditOutlined, ExclamationCircleOutlined } from '@ant-design/icons'
import { SketchPicker } from 'react-color'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import useUpdateApi from '@/hooks/use-update-api.ts'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'
import { CheckboxChangeEvent } from 'antd/es/checkbox'
import { SpecificationAttributeGroupResponse } from '@/model/SpecificationAttributeGroup'

const { Option } = Select
const { confirm } = Modal

interface Option {
    id: number
    specificationAttribute: number
    name: string
    displayOrder: number
    color: string
    associatedProducts: number
}
interface FormValues {
    name: string
    group: string
    displayOrder: number
}

interface OptionsListProps {
    options: Option[]
    onDelete: (id: number) => void
    onEdit: (record: Option) => void
}

function OptionsList({ options = [], onDelete, onEdit }: OptionsListProps) {
    const columns = [
        { title: 'Name', dataIndex: 'name', key: 'name' },
        { title: 'Display order', dataIndex: 'displayOrder', key: 'displayOrder' },
        { title: 'Number of associated products', dataIndex: 'associatedProducts', key: 'associatedProducts' },
        {
            title: 'Actions',
            key: 'actions',
            width: '20%',
            render: (record: Option) => (
                <Space size='middle'>
                    <Button type='primary' icon={<EditOutlined />} onClick={() => onEdit(record)}>
                        Edit
                    </Button>
                    <Button danger icon={<DeleteOutlined />} onClick={() => onDelete(record.id)}>
                        Delete
                    </Button>
                </Space>
            ),
        },
    ]

    return <Table columns={columns} dataSource={options} pagination={{ pageSize: 6 }} />
}

function SpecificationAttributeUpdate() {
    const [form] = Form.useForm()
    const [modalForm] = Form.useForm()
    const { id } = useParams()
    const [editData, setEditData] = useState<SpecificationAttributeResponse | null>(null)
    const [groups, setGroups] = useState<SpecificationAttributeGroupResponse[]>([])
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [isColorPickerVisible, setIsColorPickerVisible] = useState(false)
    const [color, setColor] = useState('#FFFFFF')
    const [options, setOptions] = useState<Option[]>([])
    const [editingOption, setEditingOption] = useState<Option | null>(null)
    const navigate = useNavigate()

    const formatColorHex = (hex: string) => {
        if (!/^#[0-9A-Fa-f]{6}$/.test(hex)) {
            return '#FFFFFF'
        }
        return hex.toUpperCase()
    }

    const { data, isLoading: isLoadingAttribute } = useGetByIdApi<SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
        Number(id),
    )

    const {
        data: groupData,
        isLoading: isLoadingGroups,
        error,
    } = useGetAllApi(SpecificationAttributeGroupConfigs.resourceUrl, SpecificationAttributeGroupConfigs.resourceKey)

    const { mutate: updateAttribute } = useUpdateApi<SpecificationAttributeResponse, SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
        Number(id),
    )

    const { mutate: deleteOptions } = useDeleteByIdsApi<number>(SpecificationAttributeConfigs.resourceUrl, 'options')
    const { mutate: deleteAttribute } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        'attributes',
    )

    useEffect(() => {
        if (data) {
            setEditData(data)
            form.setFieldsValue({
                name: data.name,
                group: data.specificationAttributeGroupId || 'none',
                displayOrder: data.displayOrder,
            })

            if (data.listOptions) {
                const formattedOptions = data.listOptions.map((option) => ({
                    id: option.id,
                    specificationAttribute: option.specificationAttribute,
                    name: option.name || '',
                    displayOrder: option.displayOrder || 0,
                    associatedProducts: 0,
                    color: option.colorSquaresRgb || '#FFFFFF',
                }))
                setOptions(formattedOptions)
            }
        }
    }, [data, form])

    useEffect(() => {
        if (groupData && groupData.items) {
            setGroups(groupData.items)
        }
    }, [groupData])

    if (isLoadingAttribute || isLoadingGroups) {
        return <div>Loading...</div>
    }

    if (error) {
        return <div>Error loading data: {error.message}</div>
    }

    const showEditModal = (option: Option) => {
        setEditingOption(option)
        setIsModalVisible(true)
        modalForm.setFieldsValue({
            optionName: option.name,
            optionDisplayOrder: option.displayOrder,
        })
        setColor(option.color || '#FFFFFF')
        setIsColorPickerVisible(!!option.color)
    }

    const handleCancel = () => {
        setIsModalVisible(false)
        setIsColorPickerVisible(false)
        setColor('#FFFFFF')
        modalForm.resetFields()
        setEditingOption(null)
    }

    const handleOk = () => {
        const newOption = modalForm.getFieldsValue() as {
            optionName: string
            optionDisplayOrder: number
        }
        if (!newOption.optionName) {
            message.error('Please enter a name for the option!')
            return
        }

        if (editingOption) {
            setOptions((prevOptions) =>
                prevOptions.map((option) =>
                    option.id === editingOption.id
                        ? {
                              ...option,
                              name: newOption.optionName || '',
                              displayOrder: Number(newOption.optionDisplayOrder) || 0,
                              color: isColorPickerVisible ? color : editingOption.color,
                          }
                        : option,
                ),
            )
        } else {
            setOptions((prevOptions) => [
                ...prevOptions,
                {
                    id: prevOptions.length + 1,
                    specificationAttribute: Number(id),
                    name: newOption.optionName || '',
                    displayOrder: Number(newOption.optionDisplayOrder) || 0,
                    associatedProducts: 0,
                    color: isColorPickerVisible ? color : '#FFFFFF',
                },
            ])
        }
        handleCancel()
    }

    const handleColorChange = (color: { hex: string }) => {
        setColor(color.hex)
    }

    const handleColorCheckChange = (e: CheckboxChangeEvent) => {
        setIsColorPickerVisible(e.target.checked)
        if (!e.target.checked) {
            setColor('#FFFFFF')
        }
    }

    const handleSave = () => {
        confirm({
            title: 'Do you want to save these changes?',
            icon: <ExclamationCircleOutlined />,
            content: 'Your changes will be saved permanently.',
            onOk() {
                const formValues = form.getFieldsValue() as FormValues

                const updatedOptions = options.map((option) => ({
                    id: Number(option.id),
                    name: option.name,
                    colorSquaresRgb: option.color === '#FFFFFF' ? null : option.color,
                    displayOrder: option.displayOrder,
                    productSpecificationAttributeMappings: [],
                    specificationAttributeId: Number(id),
                }))

                const dataToSend = {
                    id: Number(id),
                    name: formValues.name,
                    displayOrder: Number(formValues.displayOrder),
                    specificationAttributeGroupId: formValues.group === 'none' ? null : Number(formValues.group),
                    listOptions: updatedOptions,
                }

                updateAttribute(dataToSend, {
                    onSuccess: (result) => {
                        console.log('API response:', result)
                        navigate('/admin/specification-attribute-groups')
                    },
                    onError: (error) => {
                        console.error('Error saving attribute:', error)
                    },
                })
            },
            onCancel() {
                console.log('Cancel save operation')
            },
        })
    }

    const handleSaveAndContinue = () => {
        const formValues = form.getFieldsValue() as FormValues

        const updatedOptions = options.map((option) => ({
            id: Number(option.id),
            name: option.name,
            colorSquaresRgb: option.color === '#FFFFFF' ? null : option.color,
            displayOrder: option.displayOrder,
            productSpecificationAttributeMappings: [],
            specificationAttributeId: Number(id),
        }))

        const dataToSend = {
            id: Number(id),
            name: formValues.name,
            displayOrder: Number(formValues.displayOrder),
            specificationAttributeGroupId: formValues.group === 'none' ? null : Number(formValues.group),
            listOptions: updatedOptions,
        }

        updateAttribute(dataToSend, {
            onSuccess: () => {
                message.success('Save and continue edit successfully')
            },
            onError: () => {
                message.error('Error save and continue edit failed')
            },
        })
    }

    const handleDeleteOption = (optionId: number) => {
        confirm({
            title: 'Are you sure you want to delete this option?',
            icon: <ExclamationCircleOutlined />,
            onOk() {
                deleteOptions([optionId], {
                    onSuccess: () => {
                        setOptions((prevOptions) => prevOptions.filter((option) => option.id !== optionId))
                    },
                    onError: (error) => {
                        console.error('Error deleting option:', error)
                    },
                })
            },
            onCancel() {
                console.log('Cancel delete operation')
            },
        })
    }

    const handleDeleteAttribute = () => {
        confirm({
            title: 'Are you sure you want to delete this attribute?',
            icon: <ExclamationCircleOutlined />,
            content: 'This action cannot be undone.',
            onOk() {
                deleteAttribute([Number(id)], {
                    onSuccess: () => {
                        navigate('/admin/specification-attribute-groups')
                    },
                    onError: (error) => {
                        console.error('Error deleting attribute:', error)
                    },
                })
            },
            onCancel() {
                console.log('Cancel delete operation')
            },
        })
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Row justify='space-between' align='middle' style={{ marginBottom: '20px' }}>
                <Col>
                    <h1>Edit specification attribute details - {editData?.name || 'New Attribute'}</h1>
                </Col>
                <Col>
                    <Button type='primary' style={{ marginRight: '10px' }} onClick={handleSave}>
                        Save
                    </Button>
                    <Button type='default' style={{ marginRight: '10px' }} onClick={handleSaveAndContinue}>
                        Save and Continue Edit
                    </Button>
                    <Button danger icon={<DeleteOutlined />} onClick={handleDeleteAttribute}>
                        Delete
                    </Button>
                </Col>
            </Row>
            <Form form={form} layout='vertical' style={{ marginBottom: '20px' }}>
                <Form.Item label='Name' name='name' rules={[{ required: true, message: 'Please input the name!' }]}>
                    <Input />
                </Form.Item>
                <Form.Item label='Group' name='group'>
                    <Select defaultValue='none'>
                        <Option value='none'>None</Option>
                        {groups.map((group) => (
                            <Option key={group.id} value={group.id}>
                                {group.name}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item label='Display order' name='displayOrder'>
                    <Input type='number' />
                </Form.Item>
            </Form>
            <h2>Options</h2>
            <OptionsList options={options} onDelete={handleDeleteOption} onEdit={showEditModal} />
            <Button type='dashed' style={{ marginTop: '20px' }} onClick={() => setIsModalVisible(true)}>
                Add a new option
            </Button>

            <Modal
                title={editingOption ? 'Edit Option' : 'Add New Option'}
                open={isModalVisible}
                onOk={handleOk}
                onCancel={handleCancel}
                okText={editingOption ? 'Update' : 'Add'}
                cancelText='Cancel'
            >
                <Form form={modalForm} layout='vertical'>
                    <Form.Item
                        label='Name'
                        name='optionName'
                        rules={[{ required: true, message: 'Please input the name!' }]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item label='Specify color'>
                        <Checkbox onChange={handleColorCheckChange} checked={isColorPickerVisible}>
                            Enable color picker
                        </Checkbox>
                        {isColorPickerVisible && (
                            <div style={{ marginTop: '10px' }}>
                                <SketchPicker color={color} onChange={handleColorChange} />
                                <div style={{ marginTop: '10px' }}>
                                    <strong>Selected Color:</strong> {formatColorHex(color)}
                                </div>
                            </div>
                        )}
                    </Form.Item>
                    <Form.Item label='Display order' name='optionDisplayOrder'>
                        <Input type='number' />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}

export default SpecificationAttributeUpdate
