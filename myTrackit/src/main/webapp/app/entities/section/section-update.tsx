import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISection } from 'app/shared/model/section.model';
import { getEntity, updateEntity, createEntity, reset } from './section.reducer';

export const SectionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sectionEntity = useAppSelector(state => state.mytrackit.section.entity);
  const loading = useAppSelector(state => state.mytrackit.section.loading);
  const updating = useAppSelector(state => state.mytrackit.section.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.section.updateSuccess);

  const handleClose = () => {
    navigate('/section');
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
      ...sectionEntity,
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
          ...sectionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.section.home.createOrEditLabel" data-cy="SectionCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.section.home.createOrEditLabel">Create or edit a Section</Translate>
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
                  id="section-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.section.sectionNom')}
                id="section-sectionNom"
                name="sectionNom"
                data-cy="sectionNom"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.section.chefSection')}
                id="section-chefSection"
                name="chefSection"
                data-cy="chefSection"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.section.emailChef')}
                id="section-emailChef"
                name="emailChef"
                data-cy="emailChef"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.section.phoneChef')}
                id="section-phoneChef"
                name="phoneChef"
                data-cy="phoneChef"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/section" replace color="info">
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

export default SectionUpdate;
