import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStaff } from 'app/shared/model/staff.model';
import { getEntity, updateEntity, createEntity, reset } from './staff.reducer';

export const StaffUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const staffEntity = useAppSelector(state => state.mytrackit.staff.entity);
  const loading = useAppSelector(state => state.mytrackit.staff.loading);
  const updating = useAppSelector(state => state.mytrackit.staff.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.staff.updateSuccess);

  const handleClose = () => {
    navigate('/staff');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...staffEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...staffEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.staff.home.createOrEditLabel" data-cy="StaffCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.staff.home.createOrEditLabel">Create or edit a Staff</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="staff-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.staff.staffFname')}
                id="staff-staffFname"
                name="staffFname"
                data-cy="staffFname"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.staff.staffLname')}
                id="staff-staffLname"
                name="staffLname"
                data-cy="staffLname"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.staff.staffTitle')}
                id="staff-staffTitle"
                name="staffTitle"
                data-cy="staffTitle"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.staff.staffName')}
                id="staff-staffName"
                name="staffName"
                data-cy="staffName"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.staff.staffEmail')}
                id="staff-staffEmail"
                name="staffEmail"
                data-cy="staffEmail"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.staff.staffPhone')}
                id="staff-staffPhone"
                name="staffPhone"
                data-cy="staffPhone"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/staff" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default StaffUpdate;
