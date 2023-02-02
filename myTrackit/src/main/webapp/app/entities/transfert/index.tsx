import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Transfert from './transfert';
import TransfertDetail from './transfert-detail';
import TransfertUpdate from './transfert-update';
import TransfertDeleteDialog from './transfert-delete-dialog';

const TransfertRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Transfert />} />
    <Route path="new" element={<TransfertUpdate />} />
    <Route path=":id">
      <Route index element={<TransfertDetail />} />
      <Route path="edit" element={<TransfertUpdate />} />
      <Route path="delete" element={<TransfertDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TransfertRoutes;
